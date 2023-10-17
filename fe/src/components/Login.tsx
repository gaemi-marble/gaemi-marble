import { useNavigate } from 'react-router-dom';
import { Button, Container, Input, Title } from '../styles/common';

export default function Login() {
  const navigate = useNavigate();

  const onLogin = () => {
    // TODO: 로그인 기능 추가
    navigate('/');
  };

  return (
    <Container>
      <Title>Gaemi Marble</Title>
      <Input />
      <Input />
      <Button onClick={onLogin}>로그인</Button>
    </Container>
  );
}
