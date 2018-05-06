/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yeta.mongo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TopHistoryApplication implements ApplicationRunner {
    private static final Logger LOG = LogManager.getLogger(TopHistoryApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TopHistoryApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments applicationArguments) {
        LOG.debug("Debugging log");
        LOG.info("Info log");
        LOG.warn("Hey, This is a warning!");
        LOG.error("Oops! We have an Error. OK");
        LOG.fatal("Damn! Fatal error. Please fix me.");
    }
}
