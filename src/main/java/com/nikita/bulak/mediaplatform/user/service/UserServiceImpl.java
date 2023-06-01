package com.nikita.bulak.mediaplatform.user.service;

import com.nikita.bulak.mediaplatform.exception.AuthorNotFoundException;
import com.nikita.bulak.mediaplatform.exception.FriendNotFoundException;
import com.nikita.bulak.mediaplatform.exception.FriendsRequestNotFoundException;
import com.nikita.bulak.mediaplatform.exception.UserNotFoundException;
import com.nikita.bulak.mediaplatform.user.AuthUtils;
import com.nikita.bulak.mediaplatform.user.UserMapper;
import com.nikita.bulak.mediaplatform.user.dto.UserDto;
import com.nikita.bulak.mediaplatform.user.model.User;
import com.nikita.bulak.mediaplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Boolean createFriendsRequest(Long requestingFriendId) {
        User user = userRepository.findById(AuthUtils.getCurrentUserId()).get();
        User requestedFriend = userRepository.findById(requestingFriendId)
                .orElseThrow(() -> new UserNotFoundException("There is no user with id = " + requestingFriendId));
        user.getRequestedFriends().add(requestedFriend);
        user.getAuthors().add(requestedFriend);
        userRepository.save(user);
        return userRepository.findById(user.getId()).get().getRequestedFriends().contains(requestedFriend);
    }

    @Override
    public List<UserDto> getIncomingFriendsRequests() {
        User user = userRepository.findById(AuthUtils.getCurrentUserId()).get();
        return user.getRequestingFriends().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getOutgoingFriendsRequests() {
        User user = userRepository.findById(AuthUtils.getCurrentUserId()).get();
        return user.getRequestedFriends().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public Boolean acceptFriendsRequest(Long acceptingFriendId) {
        User user = userRepository.findById(AuthUtils.getCurrentUserId()).get();
        User requestingFriend = userRepository.findById(acceptingFriendId)
                .orElseThrow(() -> new UserNotFoundException("There is no user with id = " + acceptingFriendId));
        if (requestingFriend.getRequestedFriends().contains(user)) {
            user.getFriends().add(requestingFriend);
            user.getAuthors().add(requestingFriend);
            requestingFriend.getFriends().add(user);
            requestingFriend.getRequestedFriends().remove(user);
            userRepository.save(user);
            userRepository.save(requestingFriend);
            return true;
        } else {
            throw new FriendsRequestNotFoundException(String.format("There is no friendship request from user with userId = %s " +
                    "to user with userId = %s", requestingFriend.getId(), user.getId()));
        }
    }

    @Override
    public Boolean declineFriendsRequest(Long decliningFriendId) {
        User user = userRepository.findById(AuthUtils.getCurrentUserId()).get();
        User requestingFriend = userRepository.findById(decliningFriendId)
                .orElseThrow(() -> new UserNotFoundException("There is no user with id = " + decliningFriendId));
        if (requestingFriend.getRequestedFriends().contains(user)) {
            requestingFriend.getRequestedFriends().remove(user);
            userRepository.save(requestingFriend);
            return true;
        } else {
            throw new FriendsRequestNotFoundException(String.format("There is no friendship request from user with userId = %s " +
                    "to user with userId = %s", requestingFriend.getId(), user.getId()));
        }
    }

    @Override
    public Boolean removeFriend(Long friendId) {
        User user = userRepository.findById(AuthUtils.getCurrentUserId()).get();
        User deletedFriend = userRepository.findById(friendId)
                .orElseThrow(() -> new UserNotFoundException("There is no user with id = " + friendId));
        if (user.getFriends().contains(deletedFriend)) {
            user.getFriends().remove(deletedFriend);
            user.getAuthors().remove(deletedFriend);
            deletedFriend.getFriends().remove(user);
            userRepository.save(user);
            return true;
        } else {
            throw new FriendNotFoundException(String.format("User with userId = %s doesn't have friend with userId = %s",
                    user.getId(), deletedFriend.getId()));
        }
    }

    @Override
    public Boolean removeSubscription(Long authorId) {
        User user = userRepository.findById(AuthUtils.getCurrentUserId()).get();
        User deletedAuthor = userRepository.findById(authorId)
                .orElseThrow(() -> new UserNotFoundException("There is no user with id = " + authorId));
        if (user.getAuthors().contains(deletedAuthor)) {
            user.getAuthors().remove(deletedAuthor);
            userRepository.save(user);
            return true;
        } else {
            throw new AuthorNotFoundException(String.format("User with userId = %s doesn't have subscription to user with userId = %s",
                    user.getId(), deletedAuthor.getId()));
        }
    }

    @Override
    public UserDto getCurrentUser() {
        User user = userRepository.findById(AuthUtils.getCurrentUserId()).get();
        return UserMapper.toUserDto(user);
    }
}
