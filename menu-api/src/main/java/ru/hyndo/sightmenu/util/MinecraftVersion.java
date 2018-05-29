package ru.hyndo.sightmenu.util;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinecraftVersion implements Comparable<MinecraftVersion>, Serializable {
    /**
     * Regular expression used to parse version strings.
     */
    private static final Pattern VERSION_PATTERN = Pattern.compile( ".*\\(.*MC.\\s*([a-zA-z0-9\\-\\.]+)\\s*\\)" );

    /**
     * Version 1.12 - the world of color update.
     */
    public static final MinecraftVersion COLOR_UPDATE = new MinecraftVersion( "1.12" );

    /**
     * Version 1.11 - the exploration update.
     */
    public static final MinecraftVersion EXPLORATION_UPDATE = new MinecraftVersion( "1.11" );

    /**
     * Version 1.10 - the frostburn update.
     */
    public static final MinecraftVersion FROSTBURN_UPDATE = new MinecraftVersion( "1.10" );

    /**
     * Version 1.9 - the combat update.
     */
    public static final MinecraftVersion COMBAT_UPDATE = new MinecraftVersion( "1.9" );

    /**
     * Version 1.8 - the "bountiful" update.
     */
    public static final MinecraftVersion BOUNTIFUL_UPDATE = new MinecraftVersion( "1.8" );

    /**
     * Version 1.7.8 - the update that changed the skin format (and distribution - R.I.P. player disguise)
     */
    public static final MinecraftVersion SKIN_UPDATE = new MinecraftVersion( "1.7.8" );

    /**
     * Version 1.7.2 - the update that changed the world.
     */
    public static final MinecraftVersion WORLD_UPDATE = new MinecraftVersion( "1.7.2" );

    /**
     * Version 1.6.1 - the horse update.
     */
    public static final MinecraftVersion HORSE_UPDATE = new MinecraftVersion( "1.6.1" );

    /**
     * Version 1.5.0 - the redstone update.
     */
    public static final MinecraftVersion REDSTONE_UPDATE = new MinecraftVersion( "1.5.0" );

    /**
     * Version 1.4.2 - the scary update (Wither Boss).
     */
    public static final MinecraftVersion SCARY_UPDATE = new MinecraftVersion( "1.4.2" );

    private final int major;
    private final int minor;
    private final int build;

    /**
     * Determine the current Minecraft version.
     *
     * @param server - the Bukkit server that will be used to examine the MC version.
     */
    public MinecraftVersion( Server server ) {
        this( extractVersion( server.getVersion() ) );
    }


    /**
     * Construct a version format from the standard release version or the snapshot verison.
     *
     * @param versionOnly - the version.
     */
    private MinecraftVersion( String versionOnly ) {
        String[] section = versionOnly.split( "-" );
        int[] numbers;

        try {
            numbers = parseVersion( section[ 0 ] );

        } catch ( NumberFormatException cause ) {
            throw new IllegalArgumentException( "Cannot parse version:" + section );
        }

        this.major = numbers[ 0 ];
        this.minor = numbers[ 1 ];
        this.build = numbers[ 2 ];
    }

    /**
     * Construct a version object directly.
     *
     * @param major - major version number.
     * @param minor - minor version number.
     * @param build - build version number.
     */
    public MinecraftVersion( int major, int minor, int build ) {
        this.major = major;
        this.minor = minor;
        this.build = build;
    }

    private int[] parseVersion( String version ) {
        String[] elements = version.split( "\\." );
        int[] numbers = new int[ 3 ];

        // Make sure it's even a valid version
        if ( elements.length < 1 )
            throw new IllegalStateException( "Corrupt MC version: " + version );

        // The String 1 or 1.2 is interpreted as 1.0.0 and 1.2.0 respectively.
        for ( int i = 0; i < Math.min( numbers.length, elements.length ); i++ )
            numbers[ i ] = Integer.parseInt( elements[ i ].trim() );
        return numbers;
    }

    /**
     * Major version number
     *
     * @return Current major version number.
     */
    public int getMajor( ) {
        return major;
    }

    /**
     * Minor version number
     *
     * @return Current minor version number.
     */
    public int getMinor( ) {
        return minor;
    }

    /**
     * Build version number
     *
     * @return Current build version number.
     */
    public int getBuild( ) {
        return build;
    }


    /**
     * Retrieve the version String (major.minor.build) only.
     *
     * @return A normal version string.
     */
    public String getVersion( ) {
        return String.format( "%s.%s.%s", getMajor(), getMinor(), getBuild() );
    }

    @Override
    public int compareTo( MinecraftVersion o ) {
        if ( o == null )
            return 1;

        return ComparisonChain.start().
                compare( getMajor(), o.getMajor() ).
                compare( getMinor(), o.getMinor() ).
                compare( getBuild(), o.getBuild() ).
                result();
    }

    public boolean isAtLeast( MinecraftVersion other ) {
        return other != null && compareTo( other ) >= 0;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null )
            return false;
        if ( obj == this )
            return true;

        if ( obj instanceof MinecraftVersion ) {
            MinecraftVersion other = ( MinecraftVersion ) obj;

            return new EqualsBuilder()
                    .append( major, other.major )
                    .append( minor, other.minor )
                    .append( build, other.build )
                    .isEquals();
        }

        return false;
    }

    @Override
    public int hashCode( ) {
        return new HashCodeBuilder()
                .append( major )
                .append( minor )
                .append( build )
                .toHashCode();
    }

    @Override
    public String toString( ) {
        // Convert to a String that we can parse back again
        return String.format( "(MC: %s)", getVersion() );
    }

    /**
     * Extract the Minecraft version from CraftBukkit itself.
     *
     * @param text - the server version in text form.
     * @return The underlying MC version.
     * @throws IllegalStateException If we could not parse the version string.
     */
    public static String extractVersion( String text ) {
        Matcher version = VERSION_PATTERN.matcher( text );

        if ( version.matches() && version.group( 1 ) != null ) {
            return version.group( 1 );
        } else {
            throw new IllegalStateException( "Cannot parse version String '" + text + "'" );
        }
    }

    /**
     * Parse the given server version into a Minecraft version.
     *
     * @param serverVersion - the server version.
     * @return The resulting Minecraft version.
     */
    public static MinecraftVersion fromServerVersion( String serverVersion ) {
        return new MinecraftVersion( extractVersion( serverVersion ) );
    }

    private static MinecraftVersion currentVersion;

    public static void setCurrentVersion( MinecraftVersion version ) {
        currentVersion = version;
    }

    public static MinecraftVersion getCurrentVersion( ) {
        if ( currentVersion == null ) {
            currentVersion = fromServerVersion( Bukkit.getVersion() );
        }

        return currentVersion;
    }
}