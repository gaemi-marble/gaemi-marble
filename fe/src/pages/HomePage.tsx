import { useNavigate } from 'react-router-dom';
import { Button, Container, Title } from '../styles/common';

export default function HomePage() {
  const navigate = useNavigate();

  return (
    <Container>
      <Title onClick={() => console.log('hi')}>Gaemi Marble</Title>
      <Button onClick={() => navigate('/signin')}>게임시작</Button>
    </Container>
  );
}
