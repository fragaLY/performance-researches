package by.vk.springbootweb.transfer.api;

import by.vk.springbootweb.transfer.TransferService;
import by.vk.springbootweb.transfer.api.response.TransferResponse;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transfers")
public record TransferAPI(TransferService service) {

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<TransferResponse> transfers(@RequestParam Long originId,
      @RequestParam Long destinationId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
    return service.transfers(originId, destinationId, date);
  }
}
