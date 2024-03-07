package ru.vkbot;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
public class Bot {
    public static void main(String[] args) throws ClientException, ApiException, InterruptedException{
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        Random random = new Random();
        GroupActor actor = new GroupActor(ид группы,токен);
        Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();
        while (true){
            MessagesGetLongPollHistoryQuery historyQuery = vk.messages().getLongPollHistory(actor).ts(ts);
            List<Message> messages = historyQuery.execute().getMessages().getItems();
            if (!messages.isEmpty()){
                messages.forEach(message -> {
                    System.out.println(message.toString());
                    try {
                        vk.messages().send(actor).message("Hello!").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                    }catch (ApiException | ClientException e) {e.printStackTrace();}
                });

            }
            ts = vk.messages().getLongPollServer(actor).execute().getTs();
            Thread.sleep(500);

        }




    }
}
