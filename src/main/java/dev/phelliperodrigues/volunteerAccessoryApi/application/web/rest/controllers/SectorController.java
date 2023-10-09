package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.controllers;

import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers.ApiError;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers.ErrorDefault;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers.MethodArgumentNotValidExceptionHandler;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.requests.SectorRequest;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.SectorPageResponse;
import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.SectorResponse;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.entity.Sector;
import dev.phelliperodrigues.volunteerAccessoryApi.domain.services.SectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static dev.phelliperodrigues.volunteerAccessoryApi.utils.Endpoints.SECTOR_API;

@RestController
@RequestMapping(SECTOR_API)
@Tag(name = "Setor")
public class SectorController {

    private final SectorService sectorService;

    public SectorController(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    @Operation(
            summary = "Criar um setor",
            description = "Endpoint de criação de setor",
            tags = {"Setor"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado com sucesso", content = {@Content(schema = @Schema(implementation = SectorResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = MethodArgumentNotValidExceptionHandler.Error.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ErrorDefault.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ErrorDefault.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ErrorDefault.class), mediaType = "application/json")}),
    })
    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SectorResponse> create(@RequestBody @Valid SectorRequest request) {
        var sector = sectorService.create(request.toSector());
        return ResponseEntity.created(URI.create(SECTOR_API + "/" + sector.getId()))
                .body(SectorResponse.build(sector));

    }

    @Operation(
            summary = "Buscar setor por id",
            description = "Endpoint de busca de setor por ID",
            tags = {"Setor"}
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso", content = {@Content(schema = @Schema(implementation = SectorResponse.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404", description = "Não Encontrado", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ErrorDefault.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ErrorDefault.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ErrorDefault.class), mediaType = "application/json")}),
            }
    )
    @GetMapping(value = "/{id}")
    public ResponseEntity<SectorResponse> findById(@PathVariable String id) {
        var sector = sectorService.findById(id);
        return ResponseEntity.ok(SectorResponse.build(sector));
    }

    @Operation(
            summary = "Buscar setor por termo {id, name, active}",
            description = "Endpoint de busca de setores por termo paginada",
            tags = {"Setor"}
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso", content = {@Content(schema = @Schema(implementation = SectorPageResponse.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = {@Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500", description = "Erro interno", content = {@Content(schema = @Schema(implementation = ErrorDefault.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "401", description = "Não autorizado", content = {@Content(schema = @Schema(implementation = ErrorDefault.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "403", description = "Proibido", content = {@Content(schema = @Schema(implementation = ErrorDefault.class), mediaType = "application/json")}),
            }
    )
    @PageableAsQueryParam
    @GetMapping
    public ResponseEntity<SectorPageResponse> findAllBy(
            @RequestParam(name = "id", required = false) String id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "active", required = false) boolean active,
            @Parameter(hidden = true) Pageable pageable
    ) {
        var search = Sector.builder()
                .idByString(id)
                .name(name)
                .active(active)
                .build();

        var result = sectorService.findAllBy(search, pageable);

        var sectorPaginate = new PageImpl<>(
                result.getContent()
                        .stream()
                        .map(SectorResponse::build)
                        .toList(),
                pageable,
                result.getTotalPages()
        );

        return ResponseEntity.ok(new SectorPageResponse(sectorPaginate));
    }


}
