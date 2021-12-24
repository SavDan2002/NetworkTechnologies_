package API.GraphhopperGeocode;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Arrays;

public class GHPlace {
    @JSONField(name = "point")
    public GHPoint point;

    @JSONField(name = "name")
    public String name;

    @JSONField(name = "country")
    public String country;

    @JSONField(name = "countrycode")
    public String countrycode;

    @JSONField(name = "state")
    public String state;

    @JSONField(name = "city")
    public String city;

    @JSONField(name = "postcode")
    public String postcode;

    @JSONField(name = "osm_id")
    public String osm_id;

    @JSONField(name = "osm_type")
    public String osm_type;

    @JSONField(name = "osm_key")
    public String osm_key;

    @JSONField(name = "osm_value")
    public String osm_value;

    @JSONField(name = "extent")
    public double[] extent;

    @JSONField(name = "street")
    public String street;

    @JSONField(name = "housenumber")
    public String housenumber;

    public GHPlace() {
    }

    public String getInfo(){
        String result = new String("");

        if (country != null) {
            result = result.concat(country + ", ");
        }
        if (state != null) {
            result = result.concat(state + ", ");
        }
        if (city != null) {
            result = result.concat(city + ", ");
        }
        if (street != null) {
            result = result.concat(street + ", ");
        }
        if (housenumber != null) {
            result = result.concat(housenumber + ", ");
        }
        if (name != null) {
            result = result.concat(name + ".");
        }

        return result;
    }

    @Override
    public String toString() {
        return "Hit{" +
                "point=" + point +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", countrycode='" + countrycode + '\'' +
                ", state='" + state + '\'' +
                ", postcode='" + postcode + '\'' +
                ", osm_id='" + osm_id + '\'' +
                ", osm_type='" + osm_type + '\'' +
                ", osm_key='" + osm_key + '\'' +
                ", osm_value='" + osm_value + '\'' +
                ", extent=" + Arrays.toString(extent) +
                ", street='" + street + '\'' +
                ", housenumber='" + housenumber + '\'' +
                '}' + '\n';
    }

    public GHPoint getPoint() {
        return point;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getPostcode() {
        return postcode;
    }

    public double[] getExtent() {
        return extent;
    }

    public String getStreet() {
        return street;
    }

    public String getHousenumber() {
        return housenumber;
    }
}
