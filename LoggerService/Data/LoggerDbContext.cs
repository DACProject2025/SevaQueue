using LoggerService.Models;
using Microsoft.EntityFrameworkCore;

namespace LoggerService.Data
{
    public class LoggerDbContext : DbContext
    {
        public LoggerDbContext(DbContextOptions<LoggerDbContext> options) : base(options) { }

        public DbSet<LogEntry> Logs { get; set; }
    }
}
