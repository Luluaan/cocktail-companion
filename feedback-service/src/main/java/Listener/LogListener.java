package Listener;

import Respository.LogRepository;
import model.LogEntry;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class LogListener {
    private final LogRepository repo;

    public LogListener(LogRepository repo) {
        this.repo = repo;
    }

    @JmsListener(destination = "log.queue")
    public void onLogMessage(LogEntry entry) {
        repo.save(entry);
    }
}
