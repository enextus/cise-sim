package eu.cise.sim.api.messages.dto.label;

import eu.cise.servicemodel.v1.authority.CountryType;
import eu.cise.servicemodel.v1.authority.SeaBasinType;
import eu.cise.servicemodel.v1.service.ServiceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DiscoveryLabelDto implements Serializable {

    private static final long serialVersionUID = 42L;

    private final List<String> countryList;
    private final List<String> seaBasinList;
    private final List<String> serviceTypeList;

    private static DiscoveryLabelDto instance;

    static {

        List<String> countryList = new ArrayList<>();
        countryList.add(CountryType.EU.value());
        countryList.add(CountryType.CX.value());

        List<String> seaBasinList = new ArrayList<>();
        for (SeaBasinType type : SeaBasinType.values()) {
            seaBasinList.add(type.value());
        }

        List<String> serviceTypeList = new ArrayList<>();
        for (ServiceType type : ServiceType.values()) {
            serviceTypeList.add(type.value());
        }

        instance = new DiscoveryLabelDto(countryList, seaBasinList, serviceTypeList);
    }

    public static DiscoveryLabelDto getInstance() {
        return instance;
    }

    private DiscoveryLabelDto(List<String> countryList, List<String> seaBasinList, List<String> serviceTypeList) {
        this.countryList = countryList;
        this.seaBasinList = seaBasinList;
        this.serviceTypeList = serviceTypeList;
    }


    public List<String> getCountryList() {
        return countryList;
    }

    public List<String> getSeaBasinList() {
        return seaBasinList;
    }

    public List<String> getServiceTypeList() {
        return serviceTypeList;
    }
}
