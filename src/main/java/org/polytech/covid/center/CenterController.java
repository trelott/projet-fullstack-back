package org.polytech.covid.center;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CenterController {
    @Autowired
    private CenterService centerService;

    @GetMapping("/public/center/city/{city}")
    public List<Center> getAllByCity(@PathVariable("city") String city) {
        return centerService.findAllCenterByCity(city);
    }

    @GetMapping("/public/center/name/{name}")
    public List<Center> getAllByName(@PathVariable("name") String name) {
        return centerService.findAllCenterByName(name);
    }

    @GetMapping("/public/center/{id}")
    public Center getOneById(@PathVariable("id") Integer id) {
        return centerService.findCenterById(id);
    }

    @GetMapping("/public/center")
    public List<Center> getAll() {
        return centerService.findAllCenter();
    }


    @PostMapping(value = "/superadmin/center", consumes = {"*/*"})
    public Center createCenter(@RequestBody Center newCenter){
        return centerService.createCenter(newCenter);
    }

    @PatchMapping("superadmin/center/{id}")
    public Center updateCenter(@PathVariable("id") Integer id, @RequestBody Center updatedCenter) {
        return centerService.updateCenter(id, updatedCenter);
    }

    @DeleteMapping("superadmin/center/{id}")
    public void deleteCenter(@PathVariable("id") Integer id) {
        centerService.deleteCenterById(id);
    }
}
