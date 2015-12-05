package com.bionic.wrappers;

import com.bionic.DTO.TestDTO;

/**
 * package: com.bionic.wrappers
 * project: Test
 * class: wrapper to send tests with their result IDs to UI
 *
 * @author: Grigoriy Didorenko
 * @date: 04.12.2015
 */
public class TestWrapper {

    private TestDTO  testDTO;
    private long resultId;

    public TestWrapper(TestDTO testDTO, long resultId) {
        this.testDTO = testDTO;
        this.resultId = resultId;
    }

    public TestDTO getTestDTO() {
        return testDTO;
    }

    public void setTestDTO(TestDTO testDTO) {
        this.testDTO = testDTO;
    }

    public long getResultId() {
        return resultId;
    }

    public void setResultId(long resultId) {
        this.resultId = resultId;
    }
}
