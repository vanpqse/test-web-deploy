//package com.example.user_web_service.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class FirebaseStorageService {
//    @Autowired
//     FirebaseStorage    firebaseStorage;
//
//
//    public void uploadImage(String fileName, MultipartFile file) throws IOException {
//        // Lưu tệp tin vào bucket
//        BlobId blobId = BlobId.of("my-bucket", fileName);
//        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
//        byte[] bytes = file.getBytes();
//        try (WriteChannel writer = firebaseStorage.writer(blobInfo)) {
//            writer.write(ByteBuffer.wrap(bytes, 0, bytes.length));
//        }
//    }
//
//    public void deleteImage(String fileName) {
//        // Xóa tệp tin khỏi bucket
//        BlobId blobId = BlobId.of("my-bucket", fileName);
//        boolean deleted = firebaseStorage.delete(blobId);
//        if (deleted) {
//            System.out.println("Đã xóa tệp tin: " + fileName);
//        } else {
//            System.out.println("Không thể xóa tệp tin: " + fileName);
//        }
//    }
//
//}
