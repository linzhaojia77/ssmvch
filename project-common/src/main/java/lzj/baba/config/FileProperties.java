package lzj.baba.config;


import lombok.Data;
import lzj.baba.utils.ElAdminConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: TODO
 * @author: lzj
 * @date: 2021年07月29日 14:59
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "file")
public class FileProperties {
    /** 文件大小限制 */
    private Long maxSize;

    /** 头像大小限制 */
    private Long avatarMaxSize;

    private ElPath mac;

    private ElPath linux;

    private ElPath windows;

    public ElPath getPath(){
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith(ElAdminConstant.WIN)) {
            return windows;
        } else if(os.toLowerCase().startsWith(ElAdminConstant.MAC)){
            return mac;
        }
        return linux;
    }

    @Data
    public static class ElPath{

        private String path;

        private String avatar;
    }
}
