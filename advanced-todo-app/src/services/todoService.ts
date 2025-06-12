//import axios from "axios";
import type {Todo} from "../types/Todo";
import apiClint from "./apiClient";


export const getAllTodos = async(): Promise <Todo[]> =>{
  try{
    const response = await apiClint.get<Todo[]>('/todos');
    return Array.isArray(response.data)? response.data: [];
  }catch (error){
    console.log('Error fetching todos: ', error);
    return [];
  }
};


export const addTodoApi = async(text: string): Promise<Todo>=>{
  try {
    const response = await apiClint.post('/todos', {text});
    return response.data;
  }catch(error){
    console.log("Error adding todo: ", error);
  }
};

  



export const toggleTodoApi = async (id: number): Promise<Todo>=>{
  try{
    const response = await apiClint.patch<Todo>(`/todos/ ${id}`);
    return response.data;
  }
  catch(error){
    console.log(`Error toggling todo ${id} : `, error);
      throw error;
  }
};

export const deleteTodoApi = async(id: number): Promise<void>=>{
  try{
  await apiClint.delete(`/todos/${id}`);
  }catch(error){
  console.log(`Error deleting todo${id}: `,error);
  throw error;
  }
}