import type { Todo } from "../types/Todo";

interface TodoItemProps {
  todo: Todo;
  onToggleComplete: (id: number) => void;
  onDeleteTodo: (id: number) => void;
}

// export const TodoItem: React.FC<TodoItemProps> = (props: TodoItemProps) => {
//   const { todo, onToggleComplete, onDeleteTodo } = props;

//   return (
//     <li>
//       <span onClick={() => onToggleComplete(todo.id)}>{todo.text}</span>
//       <button onClick={() => onDeleteTodo(todo.id)}> Delete </button>
//     </li>
//   );
// }
export const TodoItem: React.FC<TodoItemProps> = ({ todo, onToggleComplete, onDeleteTodo }) => {

return (
  <li>
    <div>
      <span onClick={() => onToggleComplete(todo.id)}>{todo.text}</span> 
      <span>(author: {todo.author})</span>
    </div>
    <button onClick={() => onDeleteTodo(todo.id)}> Delete </button>
  </li>
);
}