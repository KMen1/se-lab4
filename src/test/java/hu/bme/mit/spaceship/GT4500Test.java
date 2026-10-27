package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GT4500Test {

    @Test
    void fireTorpedo_Single_Fires_From_Primary_First_Time() {
        // Arrange
        GT4500 ship = new GT4500(1, 0, 1, 0);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        boolean wasPrimaryFiredLast = ship.wasPrimaryFiredLast();
        TorpedoStore primary = ship.getPrimaryTorpedoStore();
        TorpedoStore secondary = ship.getSecondaryTorpedoStore();

        // Assert
        assertEquals(true, result);
        assertEquals(true, wasPrimaryFiredLast);
        assertEquals(0, primary.getTorpedoCount());
        assertEquals(1, secondary.getTorpedoCount());
    }

    @Test
    void fireTorpedo_Single_Fires_From_Secondary_Second_Time() {
        // Arrange
        GT4500 ship = new GT4500(1, 0, 1, 0);
        ship.fireTorpedo(FiringMode.SINGLE);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        boolean wasPrimaryFiredLast = ship.wasPrimaryFiredLast();
        TorpedoStore primary = ship.getPrimaryTorpedoStore();
        TorpedoStore secondary = ship.getSecondaryTorpedoStore();

        // Assert
        assertEquals(true, result);
        assertEquals(false, wasPrimaryFiredLast);
        assertEquals(0, primary.getTorpedoCount());
        assertEquals(0, secondary.getTorpedoCount());
    }

    @Test
    void fireTorpedo_Fires_From_Primary_When_Secondary_Is_Empty() {
        // Arrange
        GT4500 ship = new GT4500(2, 0, 0, 0);
        ship.fireTorpedo(FiringMode.SINGLE);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        boolean wasPrimaryFiredLast = ship.wasPrimaryFiredLast();
        TorpedoStore primary = ship.getPrimaryTorpedoStore();
        TorpedoStore secondary = ship.getSecondaryTorpedoStore();

        // Assert
        assertEquals(true, result);
        assertEquals(true, wasPrimaryFiredLast);
        assertEquals(0, primary.getTorpedoCount());
        assertEquals(0, secondary.getTorpedoCount());
    }

    @Test
    void fireTorpedo_Fires_From_Secondary_When_Primary_Is_Empty() {
        // Arrange
        GT4500 ship = new GT4500(0, 0, 1, 0);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        boolean wasPrimaryFiredLast = ship.wasPrimaryFiredLast();
        TorpedoStore primary = ship.getPrimaryTorpedoStore();
        TorpedoStore secondary = ship.getSecondaryTorpedoStore();

        // Assert
        assertEquals(true, result);
        assertEquals(false, wasPrimaryFiredLast);
        assertEquals(0, primary.getTorpedoCount());
        assertEquals(0, secondary.getTorpedoCount());
    }

    @Test
    void fireTorpedo_Fires_From_Alternate_Stores() {
        // Arrange
        GT4500 ship = new GT4500(2, 0, 1, 0);

        // Act
        boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
        boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);
        boolean result3 = ship.fireTorpedo(FiringMode.SINGLE);

        TorpedoStore primary = ship.getPrimaryTorpedoStore();
        TorpedoStore secondary = ship.getSecondaryTorpedoStore();

        // Assert
        assertEquals(true, result1);
        assertEquals(true, result2);
        assertEquals(true, result3);
        assertEquals(0, primary.getTorpedoCount());
        assertEquals(0, secondary.getTorpedoCount());
    }

    @Test
    void fireTorpedo_Does_Not_Fire_Secondary_When_First_Fails() {
        // Arrange
        GT4500 ship = new GT4500(1, 1, 1, 0);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        TorpedoStore primary = ship.getPrimaryTorpedoStore();
        TorpedoStore secondary = ship.getSecondaryTorpedoStore();

        // Assert
        assertEquals(false, result);
        assertEquals(1, primary.getTorpedoCount());
        assertEquals(1, secondary.getTorpedoCount());
    }

    @Test
    void fireTorpedo_Does_Not_Fire_Primary_When_Secondary_Fails() {
        // Arrange
        GT4500 ship = new GT4500(1, 0, 1, 1);
        ship.fireTorpedo(FiringMode.SINGLE);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        TorpedoStore primary = ship.getPrimaryTorpedoStore();
        TorpedoStore secondary = ship.getSecondaryTorpedoStore();

        // Assert
        assertEquals(false, result);
        assertEquals(0, primary.getTorpedoCount());
        assertEquals(1, secondary.getTorpedoCount());
    }

    @Test
    void fireTorpedo_Fails_When_Both_Stores_Are_Empty() {
        // Arrange
        GT4500 ship = new GT4500(0, 0, 0, 0);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        TorpedoStore primary = ship.getPrimaryTorpedoStore();
        TorpedoStore secondary = ship.getSecondaryTorpedoStore();

        // Assert
        assertEquals(false, result);
        assertEquals(0, primary.getTorpedoCount());
        assertEquals(0, secondary.getTorpedoCount());
    }

    @Test
    void fireTorpedo_All_Fires_Both_Stores() {
        // Arrange
        GT4500 ship = new GT4500(1, 0, 1, 0);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        TorpedoStore primary = ship.getPrimaryTorpedoStore();
        TorpedoStore secondary = ship.getSecondaryTorpedoStore();

        // Assert
        assertEquals(true, result);
        assertEquals(0, primary.getTorpedoCount());
        assertEquals(0, secondary.getTorpedoCount());
    }

    @Test
    void fireTorpedo_All_Primary_Empty_Secondary_Succeeds() {
        // Arrange
        GT4500 ship = new GT4500(0, 0, 1, 0);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        TorpedoStore primary = ship.getPrimaryTorpedoStore();
        TorpedoStore secondary = ship.getSecondaryTorpedoStore();

        // Assert
        assertEquals(true, result);
        assertEquals(0, primary.getTorpedoCount());
        assertEquals(0, secondary.getTorpedoCount());
    }

    @Test
    void fireTorpedo_All_Secondary_Empty_Primary_Succeeds() {
        // Arrange
        GT4500 ship = new GT4500(1, 0, 0, 0);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        TorpedoStore primary = ship.getPrimaryTorpedoStore();
        TorpedoStore secondary = ship.getSecondaryTorpedoStore();

        // Assert
        assertEquals(true, result);
        assertEquals(0, primary.getTorpedoCount());
        assertEquals(0, secondary.getTorpedoCount());
    }

    @Test
    void fireTorpedo_All_Primary_Fails_Secondary_Succeeds() {
        // Arrange
        GT4500 ship = new GT4500(1, 1, 1, 0);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        TorpedoStore primary = ship.getPrimaryTorpedoStore();
        TorpedoStore secondary = ship.getSecondaryTorpedoStore();

        // Assert
        assertEquals(true, result);
        assertEquals(1, primary.getTorpedoCount());
        assertEquals(0, secondary.getTorpedoCount());
    }

    @Test
    void fireTorpedo_All_Secondary_Fails_Primary_Succeeds() {
        // Arrange
        GT4500 ship = new GT4500(1, 0, 1, 1);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        TorpedoStore primary = ship.getPrimaryTorpedoStore();
        TorpedoStore secondary = ship.getSecondaryTorpedoStore();

        // Assert
        assertEquals(true, result);
        assertEquals(0, primary.getTorpedoCount());
        assertEquals(1, secondary.getTorpedoCount());
    }

    @Test
    void fireTorpedo_All_Both_Fail() {
        // Arrange
        GT4500 ship = new GT4500(1, 1, 1, 1);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        TorpedoStore primary = ship.getPrimaryTorpedoStore();
        TorpedoStore secondary = ship.getSecondaryTorpedoStore();

        // Assert
        assertEquals(false, result);
        assertEquals(1, primary.getTorpedoCount());
        assertEquals(1, secondary.getTorpedoCount());
    }

    @Test
    void fireTorpedo_All_Both_Empty() {
        // Arrange
        GT4500 ship = new GT4500(0, 0, 0, 0);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        TorpedoStore primary = ship.getPrimaryTorpedoStore();
        TorpedoStore secondary = ship.getSecondaryTorpedoStore();

        // Assert
        assertEquals(false, result);
        assertEquals(0, primary.getTorpedoCount());
        assertEquals(0, secondary.getTorpedoCount());
    }
}
