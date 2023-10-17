import { createBrowserRouter } from 'react-router-dom';

import SignInPage from '../pages/SignInPage';
import SignUpPage from '../pages/SignUpPage';
import HomePage from '../pages/HomePage';

export const router = createBrowserRouter([
  {
    path: '/',
    element: <HomePage />,
  },
  {
    path: '/signin',
    element: <SignInPage />,
  },
  {
    path: '/signup',
    element: <SignUpPage />,
  },
]);
