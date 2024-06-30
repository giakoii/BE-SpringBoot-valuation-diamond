package org.swp391.valuationdiamond.entity.primary;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tbl_Ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Rating {
  @Id
  @Column(name = "rating_id", nullable = false, length = 255)
  String ratingId;

  @Column(name = "Rating", nullable = false)
  int rating;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  User userId;

  @ManyToOne
  @JoinColumn(name = "blog_id", referencedColumnName = "blog_id")
  Blog blogId;

}
