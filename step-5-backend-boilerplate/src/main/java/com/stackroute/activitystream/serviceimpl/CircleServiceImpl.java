package com.stackroute.activitystream.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stackroute.activitystream.model.Circle;
import com.stackroute.activitystream.repository.CircleRepository;
import com.stackroute.activitystream.repository.UserRepository;
import com.stackroute.activitystream.service.CircleService;

/*
* Service classes are used here to implement additional business logic/validation. 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn’t currently 
* provide any additional behavior over the @Component annotation, but it’s a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
@Transactional
public class CircleServiceImpl implements CircleService {

	/*
	 * Autowiring should be implemented for the CircleRepository and UserRepository.
	 * Please note that we should not create any object using the new keyword
	 */

	@Autowired
	private CircleRepository circleRepository;

	@Autowired
	private UserRepository userRepository;

	/*
	 * A circle should only be created if the circle does not already exist or the
	 * creatorId is a valid username.
	 */
	public boolean save(Circle circle) {

		try {
			if (circleRepository.findOne(circle.getCircleName()) != null
					&& userRepository.findOne(circle.getCreatorId()) != null) {
				circleRepository.save(circle);
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	/*
	 * This method should return the list of existing circles
	 */
	public List<Circle> getAllCircles() {

		try {
			return circleRepository.findAll();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	/*
	 * This method should return the list of existing circles which matches the
	 * search String
	 */
	public List<Circle> getAllCircles(String searchString) {

		try {
			return circleRepository.findAll(searchString);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	/*
	 * This method should return a specific circle which matches the Circle Name
	 */
	public Circle get(String circleName) {

		try {
			return circleRepository.findOne(circleName);
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * This method should delete a specific circle(if exists)
	 */
	public boolean delete(Circle circle) {

		try {
			circleRepository.delete(circle);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

}
