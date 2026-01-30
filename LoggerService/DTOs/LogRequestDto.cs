namespace LoggerService.DTOs
{
    public class LogRequestDto
    {
        public string ServiceName { get; set; }
        public string Level { get; set; }
        public string Message { get; set; }
    }
}
