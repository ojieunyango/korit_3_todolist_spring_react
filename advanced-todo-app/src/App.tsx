import './App.css'
import { useEffect, useState } from 'react';
import type { Todo } from './types/Todo';
import TodoForm from "./components/TodoForm"
import {TodoList} from "./components/TodoList"
import { getAllTodos, addTodoApi, toggleTodoApi, deleteTodoApi } from './services/todoService';
//import { v4 as uuid } from 'uuid'; 더이상 안씀
import axios from 'axios';
import { GoogleLogin, type CredentialResponse } from '@react-oauth/google';

function App() {
 

  const [todos, setTodos] = useState<Todo[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [authToken, setAuthToken] = useState<string | null>(()=> localStorage.getItem('authToken'));

  const handleLoginSuccess = (CredentialResponse: CredentialResponse)=> {
    const idToken = CredentialResponse.credential;
    if(idToken){
      setAuthToken(idToken);
      localStorage.setItem('authToken', idToken);
    }
  }
  const handleLoginError=()=>{
    console.log('로그인에 실패했습니다.');
  }
  const handleLogout = () => {
    setAuthToken(null);
    localStorage.removeItem('authToken');
    setTodos([]);
  };

  useEffect(()=>{
    const fetchTodosFromServer = async():Promise<void> => {
     if(authToken){
      try {
        setIsLoading(true);
        const serverTodos = await getAllTodos();
        setTodos(serverTodos);
        setIsLoading(false);
      } catch(error){
         console.log('서버에서 데이터를 가지고 오는데 실패했습니다. :', error);
         if (axios.isAxiosError(error)&&(error.response?.status === 401 || error.response?.status === 403)){
         handleLogout();
         }        
      }
     }
    };
    fetchTodosFromServer();
  }, [authToken]);

  const handleAddTodo = async (text: string): Promise<void>=>{
    if(!authToken) return;
    try{
      setIsLoading(true);
      const newTodo = await addTodoApi(text);
      setTodos(prevTodos=>[...prevTodos, newTodo]);
    }catch (error){
      console.log('todo를 추가하는데 실패했습니다.: ', error);
    } finally {
      setIsLoading(false);
    }
  }



  const handleToggleComplete = async (id: number) : Promise<void> => {
    if(!authToken) return;
    try{
    const todoToToggle = todos.find(todo => todo.id === id);
    if (!todoToToggle) return;
    const updatedTodo = await toggleTodoApi(id);
    setTodos(prevTodos => 
      prevTodos.map(todo=> (todo.id === id ? updatedTodo : todo))
    );
    } catch(error){
      console.log("완료된 상태변경에 실패했습니다. :", error);
    }
  };

  const handleDeleteTodo = async(id: number):Promise<void>=>{
    if(!authToken) return;
    try{
    await deleteTodoApi(id);
    setTodos(prevTodos=> prevTodos.filter(todo=> todo.id !== id));
    }catch(error){
      console.log('todo를 지우는데 실패했습니다. :', error);
    }
  }

  return (
    <div>
      <header style={{display: 'flex', justifyContent: 'space-between', alignItems:'center', padding: '1rem'}}>
      <h1>Todo List</h1>
      <div>
        {
          authToken ? (
            <button onClick={handleLogout}> Logout</button>
          ):(
            <GoogleLogin onSuccess={handleLoginSuccess} onError={handleLoginError}/>
          )
          
        }
      </div>
      </header>
      <main>
        {
          authToken ? (
            isLoading ? (
              <p>목록을 불러오는 중입니다...</p>
            ) : (
              <>
              <TodoForm onAddTodo={handleAddTodo}/>
              <TodoList todos={todos} onToggleComplete={handleToggleComplete} onDeleteTodo={handleDeleteTodo}/>
              </>
            )
          ):(
            <h2>로그인하여 To do List를 작성해보세요. </h2>
          )
        }
      </main>
      
     
    </div>
  )
}

export default App
