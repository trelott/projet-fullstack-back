package org.polytech.covid.center;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CenterServiceTest {
    CenterService centerService;
    CenterRepository centerRepository;

    @BeforeEach
    void setup() {
        centerRepository = Mockito.mock(CenterRepository.class);
        centerService = new CenterService(centerRepository);
    }

    @Test
    void findAllCenter() {
        Center center1 = new Center();
        center1.setId(1);
        center1.setName("Centre 1");
        center1.setCity("Nancy");
        center1.setCity_code(54000);
        center1.setAddress("1 rue du centre");

        Center center2 = new Center();
        center2.setId(2);
        center2.setName("Centre 2");
        center2.setCity("Lille");
        center2.setCity_code(59000);
        center2.setAddress("1 rue du centre");

        List<Center> centerList = new ArrayList<Center>();
        centerList.add(center1);
        centerList.add(center2);

        when(centerRepository.findAll()).thenReturn(centerList);
        List<Center> returnedCenters = this.centerService.findAllCenter();

        assertEquals(centerList, returnedCenters);
    }

    @Test
    void findAllCenterByName() {
        Center center1 = new Center();
        center1.setId(1);
        center1.setName("Centre 1");
        center1.setCity("Nancy");
        center1.setCity_code(54000);
        center1.setAddress("1 rue du centre");

        when(centerRepository.findAllByName("Centre 1")).thenReturn(List.of(center1));
        List<Center> returnedCenter = this.centerService.findAllCenterByName("Centre 1");

        assertEquals(List.of(center1), returnedCenter);
    }

    @Test
    void findAllCenterByCity() {
        Center center1 = new Center();
        center1.setId(1);
        center1.setName("Centre 1");
        center1.setCity("Nancy");
        center1.setCity_code(54000);
        center1.setAddress("1 rue du centre");

        when(centerRepository.findAllByCity("Nancy")).thenReturn(List.of(center1));
        List<Center> returnedCenter = this.centerService.findAllCenterByCity("Nancy");

        assertEquals(List.of(center1), returnedCenter);
    }

    @Test
    void findCenterByIdSuccess() {
        Center center1 = new Center();
        center1.setId(1);
        center1.setName("Centre 1");
        center1.setCity("Nancy");
        center1.setCity_code(54000);
        center1.setAddress("1 rue du centre");

        when(centerRepository.findById(1)).thenReturn(Optional.of(center1));
        Center returnedCenter = this.centerService.findCenterById(1);

        assertEquals(center1, returnedCenter);
    }

    @Test
    void findCenterByIdFail() {
        when(centerRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(CenterNotFoundException.class, () -> {
            this.centerService.findCenterById(1);
        });
    }

    @Test
    void createCenter() {
        Center center1 = new Center();
        center1.setId(1);
        center1.setName("Centre 1");
        center1.setCity("Nancy");
        center1.setCity_code(54000);
        center1.setAddress("1 rue du centre");

        when(centerRepository.save(center1)).thenReturn(center1);
        Center returnedCenter = this.centerService.createCenter(center1);

        assertEquals(center1, returnedCenter);
    }

    @Test
    void updateCenterSuccess() {
        Center center1 = new Center();
        center1.setId(1);
        center1.setName("Centre 1");
        center1.setCity("Nancy");
        center1.setCity_code(54000);
        center1.setAddress("1 rue du centre");

        Center modifiedCenter1 = new Center();
        center1.setName("New centre 1 name");
        center1.setCity("Nancy");
        center1.setCity_code(54000);
        center1.setAddress("1 rue du centre");

        when(centerRepository.findById(1)).thenReturn(Optional.of(center1));

        modifiedCenter1.setId(1);

        when(centerRepository.save(modifiedCenter1)).thenReturn(modifiedCenter1);
        Center retunedCenter = this.centerService.updateCenter(1, modifiedCenter1);

        assertEquals(modifiedCenter1, retunedCenter);
    }

    @Test
    void updateCenterFail() {
        Center center1 = new Center();
        center1.setId(1);
        center1.setName("Centre 1");
        center1.setCity("Nancy");
        center1.setCity_code(54000);
        center1.setAddress("1 rue du centre");

        Center modifiedCenter1 = new Center();
        center1.setName("New centre 1 name");
        center1.setCity("Nancy");
        center1.setCity_code(54000);
        center1.setAddress("1 rue du centre");

        when(centerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CenterNotFoundException.class, () -> {
            this.centerService.updateCenter(1, modifiedCenter1);
        });
    }

    @Test
    void deleteCenterByIdSuccess() {
        Center center1 = new Center();
        center1.setId(1);
        center1.setName("Centre 1");
        center1.setCity("Nancy");
        center1.setCity_code(54000);
        center1.setAddress("1 rue du centre");

        when(centerRepository.findById(1)).thenReturn(Optional.of(center1));
        this.centerService.deleteCenterById(1);

        verify(centerRepository).deleteById(1);
    }

    @Test
    void deleteCenterByIdFail() {
        when(centerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CenterNotFoundException.class, () -> {
            this.centerService.deleteCenterById(1);
        });
    }
}