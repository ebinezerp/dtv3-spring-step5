package com.stackroute.activitystream.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.activitystream.model.Message;
import com.stackroute.activitystream.model.UserTag;
import com.stackroute.activitystream.repository.CircleRepository;
import com.stackroute.activitystream.repository.MessageRepository;
import com.stackroute.activitystream.repository.UserCircleRepository;
import com.stackroute.activitystream.repository.UserRepository;
import com.stackroute.activitystream.repository.UserTagRepository;
import com.stackroute.activitystream.service.MessageService;

/*
* Service classes are used here to implement additional business logic/validation. 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn’t currently 
* provide any additional behavior over the @Component annotation, but it’s a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service("messageeService")
@Transactional
public class MessageServiceImpl implements MessageService{
	
	/*
	 * Autowiring should be implemented for the CircleRepository, UserRepository,
	 * UserTagRepository, MessageRepository, UserCircleRepository.
	 *  Please note that we should not create any object using the new keyword
	 * */
	
	  @Autowired
	  UserRepository userRepository;
	  
	  @Autowired
	  CircleRepository circleRepository;
	  
	  @Autowired
	  UserCircleRepository userCircleRepository;
	  
	  
	  @Autowired
	  MessageRepository messageRepository;
	  
	  @Autowired
	  UserTagRepository userTagRepository;
	  
	  
	  @Autowired
	  UserTag userTag;
	
	/*
	 * This method should be used to get all messages from a specific circle. Call the corresponding method of Respository interface.
	 * */
	public List<Message> getMessagesFromCircle(String circleName,int pageNumber) {
		
		try
		{
			if(circleRepository.getOne(circleName)!=null)
			{
			 return	messageRepository.getMessagesFromCircle(circleName).subList((pageNumber-1)*10,((pageNumber-1)*10)+10);
			}else
			{
				return null;
			}
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	/*
	 * This method should be used to get all messages from a specific user to another
	 * specific user. Call the corresponding method of Respository interface.
	 */
	public List<Message> getMessagesFromUser(String username,String otherUsername,int pageNumber) {
		
		try
		{
			if(userRepository.findOne(username)!=null && userRepository.findOne(otherUsername)!=null)
			{
				return messageRepository.getMessagesFromUser(username, otherUsername).subList((pageNumber-1)*10,((pageNumber-1)*10)+10);
			}else
			{
				return null;
			}
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	/*
	 * This method should be used to send messages to a specific circle. Please validate
	 * whether the circle exists and whether the sender is subscribed to the circle. Call the corresponding method of Respository interface.
	 */
	public boolean sendMessageToCircle(String circleName,Message message) {
		
		try
		{
		   if(circleRepository.findOne(circleName)!=null)
		   {
			   messageRepository.save(message);
			   return true;
		   }else
		   {
			   return false;
		   }
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	
	/*
	 * This method should be used to send messages to a specific user. Please validate
	 * whether the sender and receiver are valid users. Call the corresponding method of Respository interface.
	 */
	public boolean sendMessageToUser(String username,Message message) {
		
		try
		{
		   if(userRepository.findOne(username)!=null && userRepository.findOne(message.getReceiverId())!=null)
		   {
			   messageRepository.save(message);
			   return true;
		   }else
		   {
			   return false;
		   }
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
	} 	
	
	/*
	 * This method should be used to list out all tags from all existing messages. Call the corresponding method of Respository interface.
	 */
	
	public List<String> listTags() {
		
		try
		{
			return messageRepository.listAllTags();
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
	
	/*
	 * This method should be used to list out all subscribed tags by a specific
	 * user. Call the corresponding method of Respository interface.
	 */
	public List<String> listMyTags(String username) {
		
		try
		{
			return messageRepository.listMyTags(username);
			
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	/*
	 * This method should be used to show all public messages(messages sent to circles)
	 * containing a specific tag. Call the corresponding method of Respository interface. 
	 */
	public List<Message> showMessagesWithTag(String tag,int pageNumber) {
		
		try
		{
		return messageRepository.showMessagesWithTag(tag).subList((pageNumber-1)*10,((pageNumber-1)*10)+10);
			
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	/*
	 * This method should be used to subscribe a user to a specific tag. Call the corresponding method of Respository interface.
	 */
	public boolean subscribeUserToTag(String username, String tag) {
		
		try
		{
			if(userRepository.findOne(username)!=null)
			{
				if(userTagRepository.getUserTag(username, tag)==null)
				{
					userTag.setTag(tag);
					userTag.setUsername(username);
					userTagRepository.save(userTag);
					return true;
				}else
				{
					return false;
				}
			}else
			{
				return false;
			}
		}catch(Exception e)
		{
			return false;
		}
	}
	
	/*
	 * This method should be used to unsubscribe a user from a specific tag. Call the corresponding method of Respository interface.
	 */
	public boolean unsubscribeUserToTag(String username, String tag) {
	
		try
		{
			if(userRepository.findOne(username)!=null)
			{
				if(userTagRepository.getUserTag(username, tag)!=null)
				{
					userTagRepository.delete(userTagRepository.getUserTag(username, tag));
					return true;
				}else
				{
					return false;
				}
			}else
			{
				return false;
			}
		}catch(Exception e)
		{
			return false;
		}
	}

}
