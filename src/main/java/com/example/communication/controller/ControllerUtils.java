package com.example.communication.controller;

import com.example.communication.model.Comment;
import com.example.communication.model.Message;
import com.example.communication.model.Role;
import com.example.communication.model.User;
import com.example.communication.repository.CommentRepository;
import com.example.communication.repository.MessageRepository;
import com.example.communication.repository.UserRepository;
import com.google.cloud.Identity;
import com.google.cloud.Policy;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageRoles;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ControllerUtils {

    private static String bucketName;

    @Value("${bucket.name}")
    public void setNameStatic(String name) {
        ControllerUtils.bucketName = name;
    }

    private static MessageRepository messageRepository;

    private static CommentRepository commentRepository;

    private static UserRepository userRepository;

    private static JdbcTemplate jdbcTemplate;

    private static Storage storage;

    public ControllerUtils(MessageRepository messageRepo,
                           UserRepository userRepo,
                           CommentRepository commentRepo,
                           JdbcTemplate jdbcTemp,
                           Storage googleStorage) {
        messageRepository = messageRepo;
        userRepository = userRepo;
        commentRepository = commentRepo;
        jdbcTemplate = jdbcTemp;
        storage = googleStorage;
    }

    public static boolean deleteMessage(Long id, User user) {
        Optional<Message> optMessage = messageRepository.findById(id);
        Message message;
        if (optMessage.isPresent()) {
            message = optMessage.get();
        } else {
            return false;
        }

        for (Comment comment : message.getComments())
            commentRepository.delete(comment);

        boolean isAdmin = user.getRoles().contains(Role.ADMIN);
        boolean isMessageCreator = message.getUser().getId().equals(user.getId());
        if (isAdmin || isMessageCreator) {
            String repostDel = "DELETE FROM reposts WHERE message_id = ?;";
            System.out.println("Reposts dependencies deleted: " + jdbcTemplate.update(repostDel, id));

            String likesDel = "DELETE FROM message_likes WHERE message_id = ?;";
            System.out.println("Likes dependencies deleted: " + jdbcTemplate.update(likesDel, id));

            messageRepository.deleteById(id);

            if (message.getFilename() != null) {
                BlobId blobId = BlobId.of(bucketName, message.getFilename());
                storage.delete(blobId);
            }
            return true;
        }

        return false;
    }

//    public static void saveMessageEntity(MultipartFile file, AbstractMessageEntity messageEntity) throws IOException {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formattedDateTime = LocalDateTime.now().format(formatter);
//        messageEntity.setPostTime(LocalDateTime.parse(formattedDateTime, formatter));
//
//        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
//            String resultFilename = fileSave(file);
//            messageEntity.setFilename(resultFilename);
//        }
//        if (messageEntity instanceof Comment) {
//            commentRepository.save((Comment) messageEntity);
//        } else {
//            messageRepository.save((Message) messageEntity);
//        }
//    }

    public static void saveComment(MultipartFile file, Comment comment) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        comment.setPostTime(LocalDateTime.parse(formattedDateTime, formatter));

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String resultFilename = fileSave(file);
            comment.setFilename(resultFilename);
        }

        commentRepository.save(comment);
    }

    public static void saveMessage(MultipartFile file, Message message) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        message.setPostTime(LocalDateTime.parse(formattedDateTime, formatter));

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String resultFilename = fileSave(file);
            message.setFilename(resultFilename);
        }

        messageRepository.save(message);
    }

    public static void saveMessage(MultipartFile file, User user) throws IOException {
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String resultFilename = fileSave(file);
            user.setProfilePic(resultFilename);
        } else {
            user.setProfilePic("default-profile-icon.png");
        }

        userRepository.save(user);
    }

    private static String fileSave(MultipartFile file) throws IOException {
        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();

        BlobId blobId = BlobId.of(bucketName, resultFilename);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        byte[] data = file.getBytes();
        storage.create(blobInfo, data);

        Policy originalPolicy = storage.getIamPolicy(bucketName);
        storage.setIamPolicy(
                bucketName,
                originalPolicy
                        .toBuilder()
                        .addIdentity(StorageRoles.objectViewer(), Identity.allUsers()) // All users can view
                        .build());

        return resultFilename;
    }
}