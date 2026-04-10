package com.hyk.portfolio.resource.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hyk.portfolio.resource.application.ResourceService;

@Slf4j
@RequiredArgsConstructor
@Component
public class ResourceCleanupScheduler {

  private final ResourceService resourceService;

  @Scheduled(cron = "${resource.cleanup-cron}")
  public void cleanupExpiredResources() {
    log.info("Starting scheduled expired resource cleanup task...");
    this.resourceService.deleteExpiredResources();
    log.info("Completed scheduled expired resource cleanup task.");
  }

}
