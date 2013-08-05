//http://emberjs.com/guides/concepts/naming-conventions/
//http://emberjs.com/guides/getting-started/displaying-the-number-of-incomplete-todos/
//http://code418.com/blog/2012/03/08/useful-emberjs-functions/

//<script src="js/models/app.js"></script>
window.Todos = Ember.Application.create();
//<script src="js/models/route.js"></script>
Todos.Router.map(function () {
  this.resource('todos', { path: '/' });
});

Todos.TodosRoute = Ember.Route.extend({
  model: function () {
    return Todos.Todo.find();
  }
});
//<script src="js/models/store.js"></script>
Todos.Store = DS.Store.extend({
  revision: 12,
  adapter: DS.FixtureAdapter.create()
});
//<script src="js/models/todo.js"></script>
Todos.Todo = DS.Model.extend({
  title: DS.attr('string'),
  isCompleted: DS.attr('boolean')
});
Todos.Todo.FIXTURES = [
 {
   id: 1,
   title: 'Learn Ember.js',
   isCompleted: true
 },
 {
   id: 2,
   title: '...',
   isCompleted: false
 },
 {
   id: 3,
   title: 'Profit!',
   isCompleted: false
 }
];
//console.log(Todos.Todo.find(1))

//<script src="js/controllers/todo_controller.js"></script>
Todos.TodoController = Ember.ObjectController.extend({
  isCompleted: function(key, value){
    var model = this.get('model');

    if (value === undefined) {
      // property being used as a getter
      return model.get('isCompleted');
    } else {
      // property being used as a setter
      model.set('isCompleted', value);
      model.save();
      return value;
    }
  }.property('model.isCompleted'),

  remaining: function () {
    console.log("X")
    return this.filterProperty('isCompleted', false).get('length');
  }.property('@each.isCompleted'),

  inflection: function () {
    var remaining = this.get('remaining');
    return remaining === 1 ? 'item' : 'items';
  }.property('remaining')
});
//<script src="js/controllers/todos_controller.js"></script>
Todos.TodosController = Ember.ArrayController.extend({
  createTodo: function () {
    // Get the todo title set by the "New Todo" text field
    var title = this.get('newTitle');
    if (!title.trim()) { return; }

    // Create the new Todo model
    var todo = Todos.Todo.createRecord({
      title: title,
      isCompleted: false
    });

    // Clear the "New Todo" text field
    this.set('newTitle', '');

    // Save the new model
    todo.save();
  }
});