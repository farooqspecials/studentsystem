package no.uio.inf5750.assignment2.model;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author Torgeir Lorange Ostby
 * @version $Id: Course.java 29 2007-08-23 19:39:08Z torgeilo $
 */
public class Course
{
	//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
    private int id;

    /**
     * Required and unique.
     */
    private String courseCode;

    /**
     * Required and unique.
     */
    private String name;
    
    @JsonIgnore
    private Set<Student> attendants = new HashSet<Student>();

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public Course()
    {
    }

    public Course( String courseCode, String name )
    {
        this.courseCode = courseCode;
        this.name = name;
    }

    // -------------------------------------------------------------------------
    // Equals and hashcode
    // -------------------------------------------------------------------------

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;

        result = result * prime + courseCode.hashCode();
        result = result * prime + name.hashCode();

        return result;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }

        if ( o == null )
        {
            return false;
        }

        if ( !(o instanceof Course) )
        {
            return false;
        }

        final Course other = (Course) o;

        return courseCode.equals( other.getCourseCode() ) && name.equals( other.getName() );
    }

    // -------------------------------------------------------------------------
    // Setters and getters
    // -------------------------------------------------------------------------
    
    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }
    @JsonProperty
    public String getCourseCode()
    {
        return courseCode;
    }

    public void setCourseCode( String courseCode )
    {
        this.courseCode = courseCode;
    }
    @JsonProperty
    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }
    @JsonIgnore
    public Set<Student> getAttendants()
    {
        //attendants = new HashSet<Student>( attendants ); // Rehash hack

        return attendants;
    }

    public void setAttendants( Set<Student> attendants )
    {
        this.attendants = attendants;
    }
}
