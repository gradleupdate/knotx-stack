/*
 * Copyright (C) 2019 Knot.x Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.knotx.stack.functional;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static io.knotx.junit5.wiremock.KnotxWiremockExtension.stubForServer;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.knotx.junit5.KnotxApplyConfiguration;
import io.knotx.junit5.KnotxExtension;
import io.knotx.junit5.RandomPort;
import io.knotx.junit5.wiremock.ClasspathResourcesMockServer;
import io.knotx.stack.KnotxServerTester;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.junit5.VertxTestContext;
import io.vertx.reactivex.core.Vertx;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

// TODO remove this test after moving scenario to knotx-http-server repository
@ExtendWith(KnotxExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class TemplatingIntegrationTest {

  @ClasspathResourcesMockServer
  private WireMockServer mockService;

  @ClasspathResourcesMockServer
  private WireMockServer mockBrokenService;

  @ClasspathResourcesMockServer
  private WireMockServer mockRepository;

  @BeforeAll
  void initMocks() {
    stubForServer(mockService,
        get(urlMatching("/service/mock/.*"))
            .willReturn(
                aResponse()
                    .withHeader("Cache-control", "no-cache, no-store, must-revalidate")
                    .withHeader("Content-Type", "application/json; charset=UTF-8")
                    .withHeader("X-Server", "Knot.x")
            ));

    stubForServer(mockBrokenService,
        get(urlMatching("/service/broken/.*"))
            .willReturn(
                aResponse()
                    .withStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
            ));

    stubForServer(mockRepository,
        get(urlMatching("/content/.*"))
            .willReturn(
                aResponse()
                    .withHeader("Cache-control", "no-cache, no-store, must-revalidate")
                    .withHeader("Content-Type", "text/html; charset=UTF-8")
                    .withHeader("X-Server", "Knot.x")
            ));
  }

  // TODO move to https://github.com/Knotx/knotx-server-http
  @Test
  @DisplayName("Expect page containing data from services when parameters specified.")
  @KnotxApplyConfiguration({"conf/application.conf",
      "scenarios/http-service/mocks.conf",
      "scenarios/http-service/tasks.conf"})
  void requestPageWithRequestParameters(VertxTestContext context, Vertx vertx,
      @RandomPort Integer globalServerPort) {
    KnotxServerTester serverTester = KnotxServerTester.defaultInstance(globalServerPort);
    serverTester.testGetRequest(context, vertx,
        "/content/fullPage.html?parameter%20with%20space=value&q=knotx",
        "results/fullPage.html");
  }

}
