### Что сделали:
## 1. Добавили области видимости (@Singleton)
Поставили @Singleton в DatabaseModule и UseCaseModule
## 2. Создали две реализации интерфейса
NotesDataSource – интерфейс источника данных
LocalNotesDataSource – работа с Room 
RemoteNotesDataSource – имитация API 
@LocalDataSource и @RemoteDataSource – квалификаторы для различения
## 3. Внедрили диспетчеры в UseCase
Добавили параметр dispatcher: CoroutineDispatcher = Dispatchers.IO
Оборачиваем вызовы в withContext(dispatcher)
## 4. Обновили репозиторий
NoteRepositoryImpl теперь принимает два источника данных через квалификаторы
Логика: сначала локально, если пусто – из сети
## 5. Обновили модули DI
DatabaseModule – провайдеры для двух реализаций и репозитория
UseCaseModule – без изменений 
### Что дало:
- Гибкость – легко подменить источник данных
- Тестируемость – можно мокать зависимости
- Контроль потоков – диспетчеры внедряются централизованно
