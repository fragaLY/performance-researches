package by.vk.springbootwebnative.transfer;

import by.vk.springbootwebnative.transfer.api.response.TransferResponse;
import by.vk.springbootwebnative.transfer.repository.TransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public record TransferService(TransferRepository repository) {

    public List<TransferResponse> transfers(Long originId, Long destinationId, Date date) {
        log.info("[TRANSFER SERVICE] Retrieving transfers");
        return repository.findByOriginIdAndDestinationIdAndDate(originId, destinationId, date)
                .parallelStream()
                .map(TransferResponse::from)
                .collect(Collectors.toList());
    }

}