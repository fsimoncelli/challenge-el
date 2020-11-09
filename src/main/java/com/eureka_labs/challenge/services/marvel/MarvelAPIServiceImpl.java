package com.eureka_labs.challenge.services.marvel;

import com.eureka_labs.challenge.domain.provider.Character;
import com.eureka_labs.challenge.domain.provider.MarvelResponse;
import com.eureka_labs.challenge.utils.JsonMarshaller;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class MarvelAPIServiceImpl implements MarvelAPIService {

    @Value("${marvel.url}")
    private String marvelEndpoint;

    @Value("${marvel.key.public}")
    private String publicKey;

    @Value("${marvel.key.private}")
    private String privateKey;

    @Autowired
    private RestTemplate marvelRestTemplate;

    @Autowired
    private JsonMarshaller jsonMarshaller;

    @Override
    public List<Character> getSuperHeroes() {

        String timestamp = new Date().toString();
        String md5Hash = generateHash(timestamp);
        String marvelURI = marvelEndpoint + "characters?ts=" + timestamp + "&apikey=" + publicKey + "&hash=" + md5Hash;

        ResponseEntity<String> responseEntity = marvelRestTemplate.getForEntity(marvelURI, String.class);
        MarvelResponse marvelResponse = (MarvelResponse) jsonMarshaller.unmarshal(responseEntity.getBody(), MarvelResponse.class);
        return marvelResponse.getData().getCharacterList();
    }

    private String generateHash(String timestamp) {
        return DigestUtils.md5Hex(timestamp.concat(privateKey).concat(publicKey));
    }

}
