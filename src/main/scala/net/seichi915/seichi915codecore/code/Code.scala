package net.seichi915.seichi915codecore.code

import scala.util.Random

object Code {
  private val alphabetList = List(
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
    'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
    'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
    'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
  )

  private def getRandomAlphabet: Char =
    alphabetList(Random.nextInt(alphabetList.size))

  private def getRandomNumber: Int = (0 to 9)(Random.nextInt((0 to 9).size))

  def generateRandomCode: Code =
    Code(
      s"$getRandomAlphabet$getRandomAlphabet$getRandomAlphabet$getRandomAlphabet",
      s"$getRandomNumber$getRandomNumber$getRandomNumber$getRandomNumber".toInt,
      s"$getRandomAlphabet$getRandomAlphabet$getRandomAlphabet$getRandomAlphabet"
    )
}

case class Code(first: String, middle: Int, last: String) {
  override def toString: String = s"$first$middle$last"
}
