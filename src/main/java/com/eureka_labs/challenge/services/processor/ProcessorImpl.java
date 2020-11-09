package com.eureka_labs.challenge.services.processor;

import com.eureka_labs.challenge.database.entity.SuperHeroe;
import com.eureka_labs.challenge.database.repository.SuperHeroeRepository;
import com.eureka_labs.challenge.domain.provider.Character;
import com.eureka_labs.challenge.services.marvel.MarvelAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessorImpl implements Processor {

    @Autowired
    private MarvelAPIService marvelService;

    @Autowired
    private SuperHeroeRepository superHeroeRepository;

    @Override
    public void loadSuperHeroes() {
        List<Character> superHeroes = marvelService.getSuperHeroes();
        for (Character marvelCharacter : superHeroes) {
            SuperHeroe superHeroe = new SuperHeroe();
            superHeroe.setId(Integer.valueOf(marvelCharacter.getId()));
            superHeroe.setName(marvelCharacter.getName());
            superHeroe.setDescription(marvelCharacter.getDescription());
            superHeroe.setResourceURI(marvelCharacter.getResourceURI());
            superHeroe.setLastUpdate(marvelCharacter.getModified());
            superHeroe.setThumbnailURI(marvelCharacter.getThumbnail().getPath());
            superHeroeRepository.save(superHeroe);
        }
    }

    @Override
    public List<SuperHeroe> getAllSuperHeroes() {
        return superHeroeRepository.findAll();
    }
}
