package com.n26.controller;

import com.n26.model.Statistic;
import com.n26.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    /**
     * Return a transaction statistics
     *
     * @return ResponseEntity<Statistic>
     */
    @GetMapping("/statistics")
    public ResponseEntity<Statistic> getStatistics() {
        return new ResponseEntity<>(statisticService.getStatistics(), OK);
    }
}
