package com.taotao.item.listener;


import com.taotao.item.viewObject.ItemVO;
import com.taotao.pojo.Item;
import com.taotao.pojo.ItemDesc;
import com.taotao.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Component
public class ItemFreemarkerListener implements MessageListener {
    private static final Logger log = LoggerFactory.getLogger(ItemFreemarkerListener.class);

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${output.dictionary}")
    private String outputPrefix;
    @Autowired
    private ItemService itemService;

    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            try {
                Long itemId = Long.valueOf(((TextMessage) message).getText());
                Item item = itemService.getItem(itemId);
                ItemDesc itemDesc = itemService.getItemDesc(itemId);
                itemStaticize("item.ftl",itemId,item,itemDesc);
            } catch (Exception e) {
                log.error("error message:",e);
            }
        }
    }

    private void itemStaticize(String templateName, Long itemId, Item item, ItemDesc itemDesc) throws IOException, TemplateException {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate(templateName);
        Map<String, Object> map = new HashMap<>();
        map.put("item",new ItemVO(item));
        map.put("itemDesc",itemDesc);
        Writer writer = new FileWriter(new File(outputPrefix+itemId+".html"));
        template.process(map,writer);
        writer.close();
    }
}
