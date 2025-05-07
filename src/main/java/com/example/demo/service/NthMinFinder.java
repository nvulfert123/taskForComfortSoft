package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

@Slf4j
@Component
public class NthMinFinder {
    public int findNthMinimum(List<Integer> numbers, int n) {

        log.debug("Finding {}-th minimum in list of {} numbers", n, numbers.size());

        if (n <= 0 || n > numbers.size()) {
            String errorMsg = String.format("Invalid N value: %d (valid range 1-%d)", n, numbers.size());
            log.error(errorMsg);
            throw new IllegalArgumentException("Invalid N value");
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int num : numbers) {
            if (maxHeap.size() < n) {
                maxHeap.offer(num);
            } else if (num < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.offer(num);
            }
        }
        log.debug("Heap contents after processing: {}", maxHeap);
        return maxHeap.peek();
    }
}