namespace LoggerService.Models
{
    public class LogEntry
    {
        public int Id { get; set; }
        public string ServiceName { get; set; }
        public string Level { get; set; } // Info, Error
        public string Message { get; set; }
        public DateTime Timestamp { get; set; } = DateTime.UtcNow;

    }
}
