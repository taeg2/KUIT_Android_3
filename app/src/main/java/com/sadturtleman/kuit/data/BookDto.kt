package com.sadturtleman.kuit.data

import com.sadturtleman.kuit.domain.Book
import kotlinx.serialization.Serializable

// Google Books API 응답 모양 그대로.
// GET https://www.googleapis.com/books/v1/volumes?q=android
// {
//   "items": [
//     { "id": "abc123", "volumeInfo": { "title": "...", "authors": ["..."], ... } },
//     ...
//   ]
// }
@Serializable
data class VolumesResponse(
    val items: List<VolumeDto> = emptyList(),   // 검색 결과 없으면 items 자체가 없음 → 기본값 필수
)

@Serializable
data class VolumeDto(
    val id: String,
    val volumeInfo: VolumeInfoDto,
)

@Serializable
data class VolumeInfoDto(
    val title: String = "(제목 없음)",
    val authors: List<String>? = null,          // 저자가 없는 책도 있다
    val description: String? = null,
    val pageCount: Int? = null,
    val publishedDate: String? = null,
)

// [정답 2]
fun VolumeDto.toBook(): Book = Book(
    id = id,
    title = volumeInfo.title,
    author = volumeInfo.authors?.joinToString(", ") ?: "작자 미상",
    pages = volumeInfo.pageCount,
    year = volumeInfo.publishedDate?.take(4),
    description = volumeInfo.description?.replace(Regex("<[^>]*>"), "")?.trim(),
)
