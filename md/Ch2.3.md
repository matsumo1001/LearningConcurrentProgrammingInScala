# Volatile variables
* JVMには揮発性変数（Volatile variables）というものがある。
* Syncronizedに比べると軽い同期化の仕組み
* ステータスフラグに利用される：計算の完了・キャンセルを表すフラグ等
* 二つの利点
  * 変数への書き込み、読み出し操作の順序替え（reorder)がおこらない
  * 書き込みが全てのスレッドで即時に可視化される

---
volatile example  
```rb
class Page(val txt: String, var position: Int)

object Volatile extends App {
  val pages = for (i <- 1 to 5) yield
    new Page("Na" * (100 - 20 * i) + " Batman!", -1)
  @volatile var found = false
  for (p <- pages) yield thread {
    var i = 0
    while (i < p.txt.length() && !found)
      if (p.txt(i) == '!') {
        p.position = i
        found = true
      } else i += 1
  }
  while(!found){}
  log(s"results: ${pages.map(_.position)}")
}

```



---

# The Java Memory Model
