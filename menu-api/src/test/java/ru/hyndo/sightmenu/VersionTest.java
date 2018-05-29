package ru.hyndo.sightmenu;

import org.bukkit.Server;
import org.junit.Test;
import ru.hyndo.sightmenu.util.MinecraftVersion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VersionTest {

    @Test
    public void testVersionComparision( ) {
        Server bukkitServer = prepareServer( "(MC: 1.8.8)" );

        MinecraftVersion serverVersion = MinecraftVersion.fromServerVersion( bukkitServer.getVersion() );

        assertTrue( serverVersion.isAtLeast( MinecraftVersion.BOUNTIFUL_UPDATE ) );
        assertFalse( serverVersion.isAtLeast( MinecraftVersion.COLOR_UPDATE ) );
    }


    @Test( expected = IllegalStateException.class )
    public void testVersionParsing( ) {
        MinecraftVersion.fromServerVersion( prepareServer( "Mainklaft 2.0" ).getVersion() );
    }

    private static Server prepareServer( String resultVersion ) {
        Server server = mock( Server.class );

        when( server.getVersion() ).thenReturn( resultVersion );

        return server;
    }
}
