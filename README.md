## 동시성 이슈를 해결하는 방법 3가지

MySQL, Spring Data JPA, Redis 사용

1. synchronized keyword
2. DB Lock
3. Redis


## synchronzied
- synchronzied 키워드를 메서드에 붙이면 각 프로세스에서 발생하는 Race Condition 문제를 해결할 수 있다. 
- 그러나 서버가 2대 이상일 경우에는 정합성을 보장해주지 못한다.

## DB Lock
1. **Optimistic Lock**
  - DB에 직접 별도의 Lock을 잡지 않고 race condition 문제가 발생할 때 version 전략을 사용하여 해결한다.
  - 별도의 Lock을 잡지 않는다는 점에서 Pessimistic Lock보다 성능상 이점이 있다. 
2. **Pessimistic Lock(Exclusive Lock)**
  - 직접 하나의 테이블에 대해 Lock을 건다.
  - 다른 트랜잭션이 특정 row의 lock을 얻는 것을 방지한다.
    - A 트랜잭션이 끝날 때까지 기다렸다가 B 트랜잭션이 Lock을 획득한다.
  - 특정 row를 update하거나 delete 할 수 있다.
  - 일반 select는 별다른 lock이 없기 때문에 조회는 가능하다.
3. **Named Lock**
  - 이름과 함께 Lock을 획득한다. 해당 Lock은 다른 세션에서 획득 및 해제가 불가능하다.
  - 트랜잭션이 종료될 때 Lock 자동으로 해제되지 않기 때문에 별도로 해제시켜주거나 선점 시간이 끝나야 한다.
  - Pessimistic Lock과 다르게 별도의 공간에 Lock을 건다.

## Redis
1. **Lettuce**
  - setnx 명령어를 활용하여 분산락을 구현
    - setnx: set if not exists
    - 기존의 value가 없을 때만 key, value를 set할 수 있다.
  - spin lock 방식이다.
    - spin lock은 임계 구역(critical section)에 진입이 불가능할 때 진입이 가능할 때까지 무한 루프를 돌면서 재시도하는 방식
    - busy-waiting 발생
  - spring data redis를 이용하면 lettuce를 기본적으로 사용가능 함

2. **Redisson**
  - pub/sub 기반으로 Lock 구현 제공
  - 채널을 하나 만들고 락을 점유중인 스레드가 락을 획득하려고 하는 스레드에게 해제를 알려주고 안내를 받은 스레드는 락 획득 시도를 한다.
  - 별도의 retry 로직을 작성하지 않아도 됨
  - 별도의 라이브러리를 사용해야 함