/**
 * 
 */
package com.mynlp;

/**
 * @author valuri1
 *
 */
public class BaseOutput {

    private final long id;
    private final String content;

    public BaseOutput(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
