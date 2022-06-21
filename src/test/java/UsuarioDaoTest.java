import br.com.alura.leilao.dao.UsuarioDao;
import br.com.alura.leilao.model.Usuario;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;


class UsuarioDaoTest {

    private EntityManager em = JPAUtil.getEntityManager();
    private UsuarioDao dao;

    @BeforeEach
    public void beforeEach() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new UsuarioDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    void deveriaEncontrarUsuarioCadastrado() {
        Usuario usuario = criaUsuario();
        Usuario encontrado = this.dao.buscarPorUsername(usuario.getNome());
        Assert.assertNotNull(encontrado);
    }

    @Test
    void naoDeveriaEncontrarUsuarioNaoCadastrado() {
        this.dao = new UsuarioDao(em);
        criaUsuario();
        Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername("beltrano"));

    }

    @Test
    void deveriaRemoverUmUsuario() {
        Usuario usuario = criaUsuario();
        dao.deletar(usuario);
        Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername(usuario.getNome()));
    }

    private Usuario criaUsuario() {
        Usuario usuario = new Usuario("fulano", "fulano@gmail.com", "12345678");
        em.persist(usuario);
        return usuario;
    }
}
