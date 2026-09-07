package com.hyk.portfolio.resource.adapter.in.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hyk.portfolio.resource.application.port.in.PurgeResourcesUseCase;

@Slf4j
@RequiredArgsConstructor
@Component
class PurgeResourcesScheduler {

  private final PurgeResourcesUseCase purgeResourcesUseCase;

  @Scheduled(cron = "${resource.purge.cron}")
  void purge() {
    int deleted = this.purgeResourcesUseCase.purge();
    if (deleted > 0) {
      log.info("연결되지 않은 리소스 {}건을 삭제했습니다", deleted);
    }
  }

}
