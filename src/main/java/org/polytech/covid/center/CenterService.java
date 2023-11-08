package org.polytech.covid.center;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CenterService {
    @Autowired
    private CenterRepository centerRepository;

    public List<Center> findAllCenter () {
        return centerRepository.findAll();
    }

    public List<Center> findAllCenterByCity (String city) {
        return centerRepository.findAllByCity(city);
    }

    public Center findCenterById (Integer id) throws CenterNotFoundException {
        Optional<Center> foundCenter = centerRepository.findById(id);

        if (foundCenter.isPresent()) return foundCenter.get();
        else throw new CenterNotFoundException();
    }

    public Center createCenter(Center newCenter) {
        return centerRepository.save(newCenter);
    }

    public Center updateCenter(Integer id, Center updatedCenter) throws CenterNotFoundException{
        Optional<Center> center = centerRepository.findById(id);

        if (center.isPresent()){
            updatedCenter.setId(center.get().getId());
            return centerRepository.save(updatedCenter);
        }
        else throw new CenterNotFoundException();

    }

    public void deleteCenterById(Integer id) throws CenterNotFoundException{
        Optional<Center> center = centerRepository.findById(id);

        if(center.isPresent()) centerRepository.deleteById(id);
        else throw new CenterNotFoundException();
    }

}
