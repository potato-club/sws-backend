package com.sws.sws.service;

import com.sws.sws.dto.library.LibraryDto;
import com.sws.sws.dto.library.LocationListResponse;
import com.sws.sws.entity.LibraryEntity;
import com.sws.sws.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import reactor.core.publisher.Mono;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LibraryService {

    private final WebClient webClient;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }

    private final LibraryRepository libraryRepository;

    private final String BASE_URL = "https://openapi.gg.go.kr/TBGGIBLLBR";
    private final String TYPE = "xml";
    private final String pIndex = "29";
    private final String pSize = "10";

    @Value("${openapi.key}")
    private String openapiKey;


    public Mono<LocationListResponse> getLibraryQuery() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("KEY", openapiKey)
                        .queryParam("TYPE", TYPE)
                        .queryParam("pIndex", pIndex)
                        .queryParam("pSize", pSize)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(this::parseXmlData);
    }

    public LocationListResponse parseXmlData(String xml) {
        LocationListResponse locationListResponse = new LocationListResponse();
        List<LibraryDto> libraryDtos = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

            document.getDocumentElement().normalize();

            NodeList rowList = document.getElementsByTagName("row");

            for (int i = 0; i < rowList.getLength(); i++) {
                Node rowNode = rowList.item(i);
                if (rowNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element rowElement = (Element) rowNode;
                    LibraryDto libraryDto = LibraryDto.builder()
                            .libraryName(getTagValue(rowElement, "LIBRRY_NM"))
                            .addr(getTagValue(rowElement, "LOCPLC_ADDR"))
                            .openTime(getTagValue(rowElement, "RECSROOM_OPEN_TM_INFO"))
                            .TelNum(getTagValue(rowElement, "TELNO"))
                            .mapX(Double.parseDouble(getTagValue(rowElement, "REFINE_WGS84_LOGT")))
                            .mapY(Double.parseDouble(getTagValue(rowElement, "REFINE_WGS84_LAT")))
                            .build();
                    libraryDtos.add(libraryDto);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse XML", e);
        }

        saveLibraries(libraryDtos);
        return locationListResponse;
    }

    private void saveLibraries(List<LibraryDto> libraryDtos) {
        List<LibraryEntity> libraries = new ArrayList<>();
        for (LibraryDto libraryDto : libraryDtos) {
            if (!libraryRepository.findByLibraryName(libraryDto.getLibraryName()).isPresent()) {
                libraries.add(libraryDto.toEntity());
            }
        }
        libraryRepository.saveAll(libraries);
    }

    private String getTagValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }

}
