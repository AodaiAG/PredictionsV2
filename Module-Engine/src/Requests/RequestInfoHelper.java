package Requests;

import java.util.UUID;

public class RequestInfoHelper
{
    private UUID requestExecutorId;
    private  UUID requestId;

    public UUID getRequestExecutorId() {
        return requestExecutorId;
    }

    public void setRequestExecutorId(UUID requestExecutorId) {
        this.requestExecutorId = requestExecutorId;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }
}
