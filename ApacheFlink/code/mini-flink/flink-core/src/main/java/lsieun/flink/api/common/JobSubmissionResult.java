package lsieun.flink.api.common;

import lsieun.flink.annotation.Public;

@Public
public class JobSubmissionResult {

    private final JobID jobID;

    public JobSubmissionResult(JobID jobID) {
        this.jobID = jobID;
    }

    public JobID getJobID() {
        return jobID;
    }

    public boolean isJobExecutionResult() {
        return this instanceof JobExecutionResult;
    }

    public JobExecutionResult getJobExecutionResult() {
        if (isJobExecutionResult()) {
            return (JobExecutionResult) this;
        }
        else {
            throw new ClassCastException("This JobSubmissionResult is not a JobExecutionResult.");
        }
    }
}
