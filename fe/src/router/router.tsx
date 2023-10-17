import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from 'react-router-dom';
import Layout from '../components/Layout';
import HomePage from '../pages/HomePage';
import SignInPage from '../pages/SignInPage';
import SignUpPage from '../pages/SignUpPage';
import { ROUTE_PATH } from './constants';

export const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path={ROUTE_PATH.SIGNIN} element={<SignInPage />} />
      <Route path={ROUTE_PATH.SIGNUP} element={<SignUpPage />} />
      <Route element={<Layout />}>
        <Route path={ROUTE_PATH.HOME} element={<HomePage />} />
      </Route>
    </>
  )
);
