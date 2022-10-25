package com.example.demo.frame;

/**
 * @author yuanlb
 * @since 2022/10/25
 */
public class ResultException extends RuntimeException{

    private ResultSource resultSource;


    public ResultException(ResultSource resultSource) {
        super(resultSource.getMessage());
        this.resultSource = resultSource;
    }


    public ResultException(ResultSource resultSource, String message) {
        super(message);
        this.resultSource = resultSource;
    }

    public ResultSource getResultSource() {
        return resultSource;
    }
}
