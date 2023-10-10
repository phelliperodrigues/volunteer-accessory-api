package dev.phelliperodrigues.volunteerAccessoryApi.domain.entity;// Generated by CodiumAI

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SectorTest {


    // Create a Sector object with all fields filled
    @Test
    void test_create_sector_with_all_fields_filled() {
        // Arrange
        UUID id = java.util.UUID.randomUUID();
        String name = "Test Sector";
        String observations = "Test observations";
        Boolean active = true;
        UUID createUserId = java.util.UUID.randomUUID();
        UUID updateUserId = java.util.UUID.randomUUID();

        // Act
        Sector sector = Sector.builder()
                .id(id)
                .name(name)
                .observations(observations)
                .active(active)
                .createUserId(createUserId)
                .updateUserId(updateUserId)
                .build();

        // Assert
        assertEquals(id, sector.getId());
        assertEquals(name, sector.getName());
        assertEquals(observations, sector.getObservations());
        assertEquals(active, sector.getActive());
        assertEquals(createUserId, sector.getCreateUserId());
        assertEquals(updateUserId, sector.getUpdateUserId());
    }

    // Create a Sector object with only required fields filled
    @Test
    void test_create_sector_with_only_required_fields_filled() {
        // Arrange
        UUID id = java.util.UUID.randomUUID();
        String name = "Test Sector";

        // Act
        Sector sector = Sector.builder()
                .id(id)
                .name(name)
                .build();

        // Assert
        assertEquals(id, sector.getId());
        assertEquals(name, sector.getName());
        assertNull(sector.getObservations());
        assertNull(sector.getActive());
        assertNull(sector.getCreateUserId());
        assertNull(sector.getUpdateUserId());
    }

    // Call isActive() method when active is a non-Boolean object
    @Test
    void test_call_isActive_with_non_Boolean_active() {
        // Arrange
        Sector sector = Sector.builder()
                .active(null)
                .build();

        // Act
        boolean result = sector.isActive();

        // Assert
        assertFalse(result);
    }

    // Call update() method with a null parameter
    @Test
    void test_call_update_with_null_parameter() {
        // Arrange
        var id = UUID.randomUUID();
        Sector sector = Sector.builder()
                .id(id)
                .name("Test Sector")
                .observations("Test Observations")
                .active(true)
                .build();
        Sector updatedSector = Sector.builder()
                .build();

        // Act
        sector.update(updatedSector);

        // Assert
        assertEquals(id, sector.getId());
        assertNull(sector.getName());
        assertNull(sector.getObservations());
        assertNull(sector.getActive());
        assertNull(sector.getUpdateUserId());
    }

    // Call SectorBuilder.idByString() with an invalid UUID string
    @Test
    void test_call_idByString_with_invalid_UUID_string() {
        // Arrange
        Sector.SectorBuilder builder = Sector.builder();

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> builder.idByString("invalid_uuid"));
    }

    @Test
    void test_call_idByString_with_valid_UUID_string() {
        // Arrange
        Sector.SectorBuilder builder = Sector.builder();

        // Act & Assert
        Sector.SectorBuilder result = builder.idByString(UUID.randomUUID().toString());
        assertNotNull(result);
    }

    @Test
    void test_call_idByString_with_null_parameter() {
        // Arrange
        var sector = Sector.builder().idByString(null).build();

        // Act & Assert
        assertNull(sector.getId());
    }

}