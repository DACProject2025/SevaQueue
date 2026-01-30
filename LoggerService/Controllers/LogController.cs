using LoggerService.Data;
using LoggerService.DTOs;
using LoggerService.Models;
using Microsoft.AspNetCore.Mvc;

namespace LoggerService.Controllers
{
    [ApiController]
    [Route("api/logs")]
    public class LogController : ControllerBase
    {
        private readonly LoggerDbContext _context;

        public LogController(LoggerDbContext context)
        {
            this._context = context;
        }

        [HttpPost]
        public IActionResult SaveLog([FromBody] LogRequestDto dto)
        {
            var log = new LogEntry
            {
                ServiceName = dto.ServiceName,
                Level = dto.Level,
                Message = dto.Message
            };

            this._context.Logs.Add(log);
            this._context.SaveChanges();

            return Ok(new { message = "Log saved successfully!" });
        }

        [HttpGet]
        public IActionResult GetAllLogs()
        {
            return Ok(this._context.Logs.OrderByDescending(l => l.Timestamp));
        }
    }
}
