package lsieun.flink.api.common;

import lsieun.flink.annotation.Public;

@Public
public class JobExecutionResult extends JobSubmissionResult {
    public JobExecutionResult(JobID jobID) {
        super(jobID);
    }
}
